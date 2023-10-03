package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileTargetReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterHistoricSpell;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public final class MoiraAndTeshar extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();
    private static final FilterPermanentCard targetFilter = new FilterPermanentCard(
            "nonland permanent card from your graveyard");

    public MoiraAndTeshar(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a historic spell, return target nonland permanent card from
        // your graveyard to the battlefield. It gains haste. Exile it at the beginning
        // of the next end step.
        Ability ability = new SpellCastControllerTriggeredAbility(new MoiraAndTesharEffect(), filter, false);
        ability.addTarget(new TargetCardInYourGraveyard(targetFilter));

        // If it would leave the battlefield, exile it instead of putting it anywhere
        // else. (Artifacts, legendaries, and Sagas are historic.)
        ability.addEffect(new LeaveBattlefieldExileTargetReplacementEffect("it"));
        this.addAbility(ability);
    }

    private MoiraAndTeshar(final MoiraAndTeshar card) {
        super(card);
    }

    @Override
    public MoiraAndTeshar copy() {
        return new MoiraAndTeshar(this);
    }
}

class MoiraAndTesharEffect extends OneShotEffect {

    MoiraAndTesharEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target nonland permanent card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    private MoiraAndTesharEffect(final MoiraAndTesharEffect effect) {
        super(effect);
    }

    @Override
    public MoiraAndTesharEffect copy() {
        return new MoiraAndTesharEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    // It gains haste
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                    // Exile at begin of next end step
                    ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(new FixedTarget(permanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }
}

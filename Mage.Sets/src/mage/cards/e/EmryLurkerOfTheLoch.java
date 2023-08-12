package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmryLurkerOfTheLoch extends CardImpl {

    public EmryLurkerOfTheLoch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // This spell costs {1} less to cast for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, ArtifactYouControlCount.instance)
        ).addHint(ArtifactYouControlHint.instance));

        // When Emry, Lurker of the Loch enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillCardsControllerEffect(4)
        ));

        // {T}: Choose target artifact card in your graveyard. You may cast that card this turn.
        Ability ability = new SimpleActivatedAbility(new EmryLurkerOfTheLochPlayEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT));
        this.addAbility(ability);
    }

    private EmryLurkerOfTheLoch(final EmryLurkerOfTheLoch card) {
        super(card);
    }

    @Override
    public EmryLurkerOfTheLoch copy() {
        return new EmryLurkerOfTheLoch(this);
    }
}

class EmryLurkerOfTheLochPlayEffect extends AsThoughEffectImpl {

    EmryLurkerOfTheLochPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Choose target artifact card in your graveyard. You may cast that card this turn.";
    }

    private EmryLurkerOfTheLochPlayEffect(final EmryLurkerOfTheLochPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EmryLurkerOfTheLochPlayEffect copy() {
        return new EmryLurkerOfTheLochPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            return targetId.equals(objectId)
                    && source.isControlledBy(affectedControllerId)
                    && Zone.GRAVEYARD == game.getState().getZone(objectId)
                    && !game.getCard(targetId).isLand(game);
        } else {
            // the target card has changed zone meanwhile, so the effect is no longer needed
            discard();
            return false;
        }
    }
}

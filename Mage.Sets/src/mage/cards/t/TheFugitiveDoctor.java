package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFugitiveDoctor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.CLUE, "a Clue");

    public TheFugitiveDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When The Fugitive Doctor enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect()));

        // Whenever The Fugitive Doctor attacks, you may sacrifice a Clue. When you do, target instant or sorcery card in your graveyard gains flashback {2}{R}{G} until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new TheFugitiveDoctorEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeTargetCost(filter), "Sacrifice a Clue?"
        )));
    }

    private TheFugitiveDoctor(final TheFugitiveDoctor card) {
        super(card);
    }

    @Override
    public TheFugitiveDoctor copy() {
        return new TheFugitiveDoctor(this);
    }
}

class TheFugitiveDoctorEffect extends ContinuousEffectImpl {

    public TheFugitiveDoctorEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback {2}{R}{G} until end of turn";
    }

    private TheFugitiveDoctorEffect(final TheFugitiveDoctorEffect effect) {
        super(effect);
    }

    @Override
    public TheFugitiveDoctorEffect copy() {
        return new TheFugitiveDoctorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card == null) {
            return false;
        }
        FlashbackAbility ability = new FlashbackAbility(card, new ManaCostsImpl<>("{2}{R}{G}"));
        ability.setSourceId(card.getId());
        ability.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability);
        return true;
    }
}

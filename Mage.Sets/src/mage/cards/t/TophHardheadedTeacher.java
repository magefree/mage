package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TophHardheadedTeacher extends CardImpl {

    public TophHardheadedTeacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Toph enters, you may discard a card. If you do, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new ReturnFromGraveyardToHandTargetEffect(), new DiscardCardCost())
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Whenever you cast a spell, earthbend 1. If that spell is a Lesson, put an additional +1/+1 counter on that land.
        ability = new SpellCastControllerTriggeredAbility(new EarthbendTargetEffect(1), false);
        ability.addEffect(new TophHardheadedTeacherEffect());
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private TophHardheadedTeacher(final TophHardheadedTeacher card) {
        super(card);
    }

    @Override
    public TophHardheadedTeacher copy() {
        return new TophHardheadedTeacher(this);
    }
}

class TophHardheadedTeacherEffect extends OneShotEffect {

    TophHardheadedTeacherEffect() {
        super(Outcome.Benefit);
        staticText = "If that spell is a Lesson, put an additional +1/+1 counter on that land";
    }

    private TophHardheadedTeacherEffect(final TophHardheadedTeacherEffect effect) {
        super(effect);
    }

    @Override
    public TophHardheadedTeacherEffect copy() {
        return new TophHardheadedTeacherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return spell != null && permanent != null
                && spell.hasSubtype(SubType.LESSON, game)
                && permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
    }
}

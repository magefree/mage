package mage.cards.o;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.*;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmnathLocusOfCreation extends CardImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public OmnathLocusOfCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Omnath, Locus of Creation enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Landfall — Whenever a land enters the battlefield under your control, you gain 4 life if this is the first time this ability has resolved this turn. If it's the second time, add {R}{G}{W}{U}. If it's the third time, Omnath deals 4 damage to each opponent and each planeswalker you don't control.
        Ability ability = new LandfallAbility(new IfAbilityHasResolvedXTimesEffect(
                Outcome.GainLife, 1, new GainLifeEffect(4)
        ).setText("you gain 4 life if this is the first time this ability has resolved this turn."), false);
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.PutManaInPool, 2, new BasicManaEffect(new Mana(
                1, 1, 0, 1, 1, 0, 0, 0
        ))).setText("If it's the second time, add {R}{G}{W}{U}."));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.Damage, 3, new DamagePlayersEffect(4, TargetController.OPPONENT)
        ).setText("If it's the third time, {this} deals 4 damage to each opponent"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.Damage, 3, new DamageAllEffect(4, filter)
        ).setText("and each planeswalker you don't control."));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private OmnathLocusOfCreation(final OmnathLocusOfCreation card) {
        super(card);
    }

    @Override
    public OmnathLocusOfCreation copy() {
        return new OmnathLocusOfCreation(this);
    }
}

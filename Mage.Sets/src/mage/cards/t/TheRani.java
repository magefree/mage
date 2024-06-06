package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.game.Game;
import mage.game.permanent.token.MarkOfTheRaniToken;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author grimreap124
 */
public final class TheRani extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a goaded creature");

    static {
        filter.add(GoadedPredicate.instance);
    }

    public TheRani(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever The Rani enters the battlefield or attacks, create a red Aura enchantment token named Mark of the Rani attached to another target creature. That token has enchant creature and "Enchanted creature gets +2/+2 and is goaded."
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new TheRaniEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE));
        this.addAbility(ability);

        // Whenever a goaded creature deals combat damage to one of your opponents, investigate.
        Ability damageDealtAbility = new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new InvestigateEffect(),
                filter, false, SetTargetPointer.NONE,
                true, false, TargetController.OPPONENT)
                .setTriggerPhrase("Whenever a goaded creature deals combat damage to one of your opponents, ");
        this.addAbility(damageDealtAbility);
    }

    private TheRani(final TheRani card) {
        super(card);
    }

    @Override
    public TheRani copy() {
        return new TheRani(this);
    }
}

class TheRaniEffect extends OneShotEffect {

    TheRaniEffect() {
        super(Outcome.Benefit);
        staticText = "create a red Aura enchantment token named Mark of the Rani attached to another target creature. That token has enchant creature and \"Enchanted creature gets +2/+2 and is goaded.\"";
    }

    private TheRaniEffect(final TheRaniEffect effect) {
        super(effect);
    }

    @Override
    public TheRaniEffect copy() {
        return new TheRaniEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        new MarkOfTheRaniToken().putOntoBattlefield(
                1, game, source, source.getControllerId(),
                false, false, null, targetId);

        return true;
    }
}

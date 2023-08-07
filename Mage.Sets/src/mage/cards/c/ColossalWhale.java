package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class ColossalWhale extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public ColossalWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.WHALE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Colossal Whale attacks, you may exile target creature defending player controls until Colossal Whale leaves the battlefield.
        Ability ability = new AttacksTriggeredAbility(new ExileUntilSourceLeavesEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private ColossalWhale(final ColossalWhale card) {
        super(card);
    }

    @Override
    public ColossalWhale copy() {
        return new ColossalWhale(this);
    }
}

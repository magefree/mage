package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperousThief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Ninja or Rogue creatures");

    static {
        filter.add(Predicates.or(SubType.NINJA.getPredicate(), SubType.ROGUE.getPredicate()));
    }

    public ProsperousThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{U}
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // Whenever one or more Ninja or Rogue creatures you control deal combat damage to a player, create a Treasure token.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new CreateTokenEffect(new TreasureToken()), filter));
    }

    private ProsperousThief(final ProsperousThief card) {
        super(card);
    }

    @Override
    public ProsperousThief copy() {
        return new ProsperousThief(this);
    }
}
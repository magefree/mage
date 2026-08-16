package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.permanent.token.RatRogueToken;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Duration;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BossUramonShadowsReach extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("attacking Ninjas and Rogues you control");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Ninjas and/or Rogues you control");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.or(
            SubType.NINJA.getPredicate(),
            SubType.ROGUE.getPredicate()
        ));

        filter2.add(Predicates.or(
            SubType.NINJA.getPredicate(),
            SubType.ROGUE.getPredicate()
        ));
    }

    public BossUramonShadowsReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // Attacking Ninjas and Rogues you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever one or more Ninjas and/or Rogues you control deal combat damage to a player, create a 1/1 black Rat Rogue creature token.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(new CreateTokenEffect(new RatRogueToken()), filter2));
    }

    private BossUramonShadowsReach(final BossUramonShadowsReach card) {
        super(card);
    }

    @Override
    public BossUramonShadowsReach copy() {
        return new BossUramonShadowsReach(this);
    }
}

package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlaringFleshraker extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a colorless spell");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("another colorless creature");

    static {
        filter.add(ColorlessPredicate.instance);
        filter2.add(AnotherPredicate.instance);
        filter2.add(ColorlessPredicate.instance);
    }

    public GlaringFleshraker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a colorless spell, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new EldraziSpawnToken()), filter, false
        ));

        // Whenever another colorless creature enters the battlefield under your control, Glaring Fleshraker deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), filter2
        ));
    }

    private GlaringFleshraker(final GlaringFleshraker card) {
        super(card);
    }

    @Override
    public GlaringFleshraker copy() {
        return new GlaringFleshraker(this);
    }
}

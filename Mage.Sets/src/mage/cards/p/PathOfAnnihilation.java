package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class PathOfAnnihilation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ELDRAZI, "Eldrazi");
    private static final FilterCreatureSpell filter2 = new FilterCreatureSpell("a creature spell with mana value 7 or greater");

    static {
        filter2.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 7));
    }

    public PathOfAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When Path of Annihilation enters the battlefield, create two 0/1 colorless Eldrazi Spawn creature tokens with "Sacrifice this creature: Add {C}."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 2)));

        // Eldrazi you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever you cast a creature spell with mana value 7 or greater, you gain 4 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(4), filter2, false));
    }

    private PathOfAnnihilation(final PathOfAnnihilation card) {
        super(card);
    }

    @Override
    public PathOfAnnihilation copy() {
        return new PathOfAnnihilation(this);
    }
}

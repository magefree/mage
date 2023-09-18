
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class HazoretTheFervent extends CardImpl {

    public HazoretTheFervent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Hazoret the Fervent can't attack or block unless you have one or fewer cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantAttackBlockUnlessConditionSourceEffect(new CardsInHandCondition(ComparisonType.FEWER_THAN, 2))
                        .setText("{this} can't attack or block unless you have one or fewer cards in hand")));

        // {2}{R}, Discard a card: Hazoret deals 2 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(2, TargetController.OPPONENT), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private HazoretTheFervent(final HazoretTheFervent card) {
        super(card);
    }

    @Override
    public HazoretTheFervent copy() {
        return new HazoretTheFervent(this);
    }
}

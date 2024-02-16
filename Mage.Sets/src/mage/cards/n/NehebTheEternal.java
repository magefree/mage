package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class NehebTheEternal extends CardImpl {

    public NehebTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Afflict 3
        addAbility(new AfflictAbility(3));

        // At the beginning of your postcombat main phase, add {R} for each 1 life your opponents have lost this turn.
        this.addAbility(
                new BeginningOfPostCombatMainTriggeredAbility(
                        new DynamicManaEffect(
                                Mana.RedMana(1),
                                OpponentsLostLifeCount.instance,
                                "add {R} for each 1 life your opponents have lost this turn"),
                        TargetController.YOU,
                        false));
    }

    private NehebTheEternal(final NehebTheEternal card) {
        super(card);
    }

    @Override
    public NehebTheEternal copy() {
        return new NehebTheEternal(this);
    }
}

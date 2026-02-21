package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class OrokuSakiShredderRising extends CardImpl {

    public OrokuSakiShredderRising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Sneak {1}{B}
        this.addAbility(new SneakAbility(this, "{1}{B}"));

        // Whenever Oroku Saki deals combat damage to a player, you draw a card and lose 1 life.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(1, true), false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private OrokuSakiShredderRising(final OrokuSakiShredderRising card) {
        super(card);
    }

    @Override
    public OrokuSakiShredderRising copy() {
        return new OrokuSakiShredderRising(this);
    }
}

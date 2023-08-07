package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PhyrexianGargantua extends CardImpl {

    public PhyrexianGargantua(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2, "you"), false);
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private PhyrexianGargantua(final PhyrexianGargantua card) {
        super(card);
    }

    @Override
    public PhyrexianGargantua copy() {
        return new PhyrexianGargantua(this);
    }
}

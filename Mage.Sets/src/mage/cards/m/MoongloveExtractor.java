package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoongloveExtractor extends CardImpl {

    public MoongloveExtractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever this creature attacks, you draw a card and lose 1 life.
        Ability ability = new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability);
    }

    private MoongloveExtractor(final MoongloveExtractor card) {
        super(card);
    }

    @Override
    public MoongloveExtractor copy() {
        return new MoongloveExtractor(this);
    }
}

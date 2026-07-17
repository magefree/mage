package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shimmercreep extends CardImpl {

    public Shimmercreep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // Vivid -- When this creature enters, each opponent loses X life and you gain X life, where X is the number of colors among permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new LoseLifeOpponentsEffect(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
                        .setText("each opponent loses X life")
        );
        ability.addEffect(new GainLifeEffect(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
                .setText("and you gain X life, where X is the number of colors among permanents you control"));
        this.addAbility(ability.setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private Shimmercreep(final Shimmercreep card) {
        super(card);
    }

    @Override
    public Shimmercreep copy() {
        return new Shimmercreep(this);
    }
}

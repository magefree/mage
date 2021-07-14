package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfHadar extends CardImpl {

    public HeraldOfHadar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Circle of Death - {5}{B}: Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new SimpleActivatedAbility(
                effect, new ManaCostsImpl<>("{5}{B}")
        ).withFlavorWord("Circle of Death"));

        // 1-9 | Each opponent loses 2 life.
        effect.addTableEntry(
                1, 9,
                new LoseLifeOpponentsEffect(2)
        );

        // 10-19 | Each opponent loses 2 life and you gain 2 life.
        effect.addTableEntry(
                10, 19,
                new LoseLifeOpponentsEffect(2),
                new GainLifeEffect(2).concatBy("and")
        );

        // 20 | Each opponent loses 2 life and you gain 2 life. Create two Treasure tokens.
        effect.addTableEntry(
                20, 20,
                new LoseLifeOpponentsEffect(2),
                new GainLifeEffect(2).concatBy("and"),
                new CreateTokenEffect(new TreasureToken(), 2).concatBy(".")
        );
    }

    private HeraldOfHadar(final HeraldOfHadar card) {
        super(card);
    }

    @Override
    public HeraldOfHadar copy() {
        return new HeraldOfHadar(this);
    }
}

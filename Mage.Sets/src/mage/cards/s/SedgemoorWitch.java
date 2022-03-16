package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Pest11GainLifeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SedgemoorWitch extends CardImpl {

    public SedgemoorWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Ward—Pay 3 life.
        this.addAbility(new WardAbility(new PayLifeCost(3)));

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, create a 1/1 black and green Pest creature token with "Whenever this creature dies, you gain 1 life."
        this.addAbility(new MagecraftAbility(new CreateTokenEffect(new Pest11GainLifeToken())));
    }

    private SedgemoorWitch(final SedgemoorWitch card) {
        super(card);
    }

    @Override
    public SedgemoorWitch copy() {
        return new SedgemoorWitch(this);
    }
}

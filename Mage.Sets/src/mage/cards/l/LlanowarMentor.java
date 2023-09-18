package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.LlanowarElvesToken;

/**
 *
 * @author LoneFox
 */
public final class LlanowarMentor extends CardImpl {

    public LlanowarMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {T}, Discard a card: Create a 1/1 green Elf Druid creature token named Llanowar Elves. It has "{T}: Add {G}."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new LlanowarElvesToken()), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private LlanowarMentor(final LlanowarMentor card) {
        super(card);
    }

    @Override
    public LlanowarMentor copy() {
        return new LlanowarMentor(this);
    }
}

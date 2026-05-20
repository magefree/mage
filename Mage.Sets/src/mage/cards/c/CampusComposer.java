package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Elemental33BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CampusComposer extends PrepareCard {

    public CampusComposer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}", "Aqueous Aria", new CardType[]{CardType.SORCERY}, "{4}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Aqueous Aria
        // Sorcery {4}{U}
        // Create a 3/3 blue and red Elemental creature token with flying.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new Elemental33BlueRedToken()));
    }

    private CampusComposer(final CampusComposer card) {
        super(card);
    }

    @Override
    public CampusComposer copy() {
        return new CampusComposer(this);
    }
}

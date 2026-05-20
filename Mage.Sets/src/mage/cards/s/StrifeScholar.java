package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Spirit22RedWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrifeScholar extends PrepareCard {

    public StrifeScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}", "Awaken the Ages", CardType.SORCERY, "{5}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2)));

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Awaken the Ages
        // Sorcery {5}{R}
        // Create two 2/2 red and white Spirit creature tokens.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new Spirit22RedWhiteToken(), 2));
    }

    private StrifeScholar(final StrifeScholar card) {
        super(card);
    }

    @Override
    public StrifeScholar copy() {
        return new StrifeScholar(this);
    }
}

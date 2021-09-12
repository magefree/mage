package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RiseOfTheAntsToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseOfTheAnts extends CardImpl {

    public RiseOfTheAnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Create two 3/3 green Insect creature tokens. You gain 2 life.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RiseOfTheAntsToken(), 2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("."));

        // Flashback {6}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{G}{G}")));
    }

    private RiseOfTheAnts(final RiseOfTheAnts card) {
        super(card);
    }

    @Override
    public RiseOfTheAnts copy() {
        return new RiseOfTheAnts(this);
    }
}

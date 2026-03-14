
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author fireshoes
 */
public final class OjutaiMonument extends CardImpl {

    public OjutaiMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // {4}{W}{U}: Ojutai Monument becomes a 4/4 white and blue Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    4, 4, "4/4 white and blue Dragon artifact creature with flying", SubType.DRAGON
                ).withColor("WU").withType(CardType.ARTIFACT).withAbility(FlyingAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{4}{W}{U}")
        ));
    }

    private OjutaiMonument(final OjutaiMonument card) {
        super(card);
    }

    @Override
    public OjutaiMonument copy() {
        return new OjutaiMonument(this);
    }
}

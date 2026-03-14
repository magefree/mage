package mage.cards.o;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrzhovKeyrune extends CardImpl {

    public OrzhovKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {W}{B}: Orzhov Keyrune becomes a 1/4 white and black Thrull artifact creature with lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    1, 4, "1/4 white and black Thrull artifact creature with lifelink", SubType.THRULL
                ).withColor("WB").withType(CardType.ARTIFACT).withAbility(LifelinkAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{W}{B}")
        ));
    }

    private OrzhovKeyrune(final OrzhovKeyrune card) {
        super(card);
    }

    @Override
    public OrzhovKeyrune copy() {
        return new OrzhovKeyrune(this);
    }
}

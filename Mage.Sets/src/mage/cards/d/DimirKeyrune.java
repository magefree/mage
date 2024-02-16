
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LevelX2
 */
public final class DimirKeyrune extends CardImpl {

    public DimirKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // {U}{B}: Dimir Keyrune becomes a 2/2 blue and black Horror and can't be blocked this turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 blue and black Horror creature that can't be blocked")
                .withColor("UB")
                .withSubType(SubType.HORROR)
                .withAbility(new CantBeBlockedSourceAbility()),
                CardType.ARTIFACT, Duration.EndOfTurn).withDurationRuleAtStart(true), new ManaCostsImpl<>("{U}{B}")));
    }

    private DimirKeyrune(final DimirKeyrune card) {
        super(card);
    }

    @Override
    public DimirKeyrune copy() {
        return new DimirKeyrune(this);
    }
}


package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
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
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

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
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new DimirKeyruneToken(), "", Duration.EndOfTurn), new ManaCostsImpl("{U}{B}")));
    }

    public DimirKeyrune(final DimirKeyrune card) {
        super(card);
    }

    @Override
    public DimirKeyrune copy() {
        return new DimirKeyrune(this);
    }

    private static class DimirKeyruneToken extends TokenImpl {
        DimirKeyruneToken() {
            super("Horror", "2/2 blue and black Horror until end of turn and can't be blocked this turn");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            color.setBlack(true);
            subtype.add(SubType.HORROR);
            power = new MageInt(2);
            toughness = new MageInt(2);
            this.addAbility(new CantBeBlockedSourceAbility());
        }
        public DimirKeyruneToken(final DimirKeyruneToken token) {
            super(token);
        }

        public DimirKeyruneToken copy() {
            return new DimirKeyruneToken(this);
        }
    }
}

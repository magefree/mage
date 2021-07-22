package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoathsomeTroll extends CardImpl {

    public LoathsomeTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(2);

        // {3}{G}: Roll a d20. Activate only if Loathsome Troll is in your graveyard.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "roll a d20. Activate only if {this} is in your graveyard"
        );
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, effect, new ManaCostsImpl<>("{3}{G}")));

        // 1-9 | Put Loathsome Troll on top of your library.
        effect.addTableEntry(1, 9, new PutOnLibrarySourceEffect(
                true, "put {this} on top of your library"
        ));

        // 10-19 | Return Loathsome Troll to your hand.
        effect.addTableEntry(10, 19, new ReturnToHandSourceEffect().setText("return {this} to your hand"));

        // 20 | Return Loathsome Troll to the battlefield tapped.
        effect.addTableEntry(20, 20, new ReturnSourceFromGraveyardToBattlefieldEffect(true)
                .setText("return {this} to the battlefield tapped"));
    }

    private LoathsomeTroll(final LoathsomeTroll card) {
        super(card);
    }

    @Override
    public LoathsomeTroll copy() {
        return new LoathsomeTroll(this);
    }
}

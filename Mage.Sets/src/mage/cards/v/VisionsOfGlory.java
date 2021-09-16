package mage.cards.v;

import mage.abilities.costs.costadjusters.CommanderManaValueAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionsOfGlory extends CardImpl {

    public VisionsOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Create a 1/1 white Human creature token for each creature you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new HumanToken(), CreaturesYouControlCount.instance
        ).setText("create a 1/1 white Human creature token for each creature you control"));

        // Flashback {8}{W}{W}. This spell costs {X} less to cast this way, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{W}{W}"))
                .setAbilityName("This spell costs {X} less to cast this way, where X is the greatest mana value " +
                        "of a commander you own on the battlefield or in the command zone.")
                .setCostAdjuster(CommanderManaValueAdjuster.instance));
    }

    private VisionsOfGlory(final VisionsOfGlory card) {
        super(card);
    }

    @Override
    public VisionsOfGlory copy() {
        return new VisionsOfGlory(this);
    }
}

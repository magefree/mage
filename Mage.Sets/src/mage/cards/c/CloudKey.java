package mage.cards.c;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllOfChosenCardTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author nick.myers
 */
public final class CloudKey extends CardImpl {

    public CloudKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Cloud Key enters the battlefield, choose artifact, creature, enchantment, instant, or sorcery.
        this.addAbility(new AsEntersBattlefieldAbility(
                new ChooseCardTypeEffect(Outcome.Benefit, Arrays.asList(CardType.ARTIFACT, CardType.CREATURE, CardType.ENCHANTMENT, CardType.INSTANT, CardType.SORCERY))
                        .setText("choose artifact, creature, enchantment, instant, or sorcery")
        ));

        // Spells you cast of the chosen type cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostReductionAllOfChosenCardTypeEffect(new FilterCard("Spells you cast of the chosen type"), 1, true)
        ));
    }

    @Override
    public CloudKey copy() {
        return new CloudKey(this);
    }

    private CloudKey(final CloudKey card) {
        super(card);
    }
}

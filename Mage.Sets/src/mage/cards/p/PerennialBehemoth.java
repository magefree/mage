package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerennialBehemoth extends CardImpl {

    public PerennialBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // You may play land cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(new PlayLandsFromGraveyardControllerEffect()));

        // Unearth {G}{G}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{G}{G}")));
    }

    private PerennialBehemoth(final PerennialBehemoth card) {
        super(card);
    }

    @Override
    public PerennialBehemoth copy() {
        return new PerennialBehemoth(this);
    }
}

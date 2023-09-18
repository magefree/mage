package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class ArgothianPixies extends CardImpl {

    public ArgothianPixies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Argothian Pixies can't be blocked by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, Duration.WhileOnBattlefield)));

        // Prevent all damage that would be dealt to Argothian Pixies by artifact creatures.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE)));
    }

    private ArgothianPixies(final ArgothianPixies card) {
        super(card);
    }

    @Override
    public ArgothianPixies copy() {
        return new ArgothianPixies(this);
    }
}

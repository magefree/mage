package mage.cards.e;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EmmaraVoiceOfTheConclave extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
        "Ancient Imperiosaur",
        "Conclave Tribunal",
        "Knight-Errant of Eos",
        "Loxodon Restorer",
        "March of the Multitudes",
        "Nissa's Expedition",
        "Overwhelm",
        "Triplicate Spirits",
        "Venerated Loxodon"
    ));

    public EmmaraVoiceOfTheConclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Emmara, Voice of the Conclave enters, draft a card from Emmara, Voice of the Conclave's spellbook.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DraftFromSpellbookEffect(spellbook)));
    }

    private EmmaraVoiceOfTheConclave(final EmmaraVoiceOfTheConclave card) {
        super(card);
    }

    @Override
    public EmmaraVoiceOfTheConclave copy() {
        return new EmmaraVoiceOfTheConclave(this);
    }
}

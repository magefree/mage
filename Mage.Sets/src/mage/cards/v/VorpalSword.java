package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VorpalSword extends CardImpl {

    public VorpalSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has deathtouch.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has deathtouch"));
        this.addAbility(ability);

        // {5}{B}{B}{B}: Until end of turn, Vorpal Sword gains "Whenever equipped creature deals combat damage to a player, that player loses the game."
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                new DealsDamageToAPlayerAttachedTriggeredAbility(
                        new LoseGameTargetPlayerEffect(), "equipped creature",
                        false, true
                ), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{5}{B}{B}{B}")));

        // Equip {B}{B}
        this.addAbility(new EquipAbility(Outcome.Benefit, new ManaCostsImpl<>("{B}{B}"), false));
    }

    private VorpalSword(final VorpalSword card) {
        super(card);
    }

    @Override
    public VorpalSword copy() {
        return new VorpalSword(this);
    }
}
// ’Twas brillig, and the slithy toves
// Did gyre and gimble in the wabe:
// All mimsy were the borogoves,
// And the mome raths outgrabe.

// “Beware the Jabberwock, my son!
// The jaws that bite, the claws that catch!
// Beware the Jubjub bird, and shun
// The frumious Bandersnatch!”

// He took his vorpal sword in hand;
// Long time the manxome foe he sought—
// So rested he by the Tumtum tree
// And stood awhile in thought.

// And, as in uffish thought he stood,
// The Jabberwock, with eyes of flame,
// Came whiffling through the tulgey wood,
// And burbled as it came!

// One, two! One, two! And through and through
// The vorpal blade went snicker-snack!
// He left it dead, and with its head
// He went galumphing back.

// “And hast thou slain the Jabberwock?
// Come to my arms, my beamish boy!
// O frabjous day! Callooh! Callay!”
// He chortled in his joy.

// ’Twas brillig, and the slithy toves
// Did gyre and gimble in the wabe:
// All mimsy were the borogoves,
// And the mome raths outgrabe.

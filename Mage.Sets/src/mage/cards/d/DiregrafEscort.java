
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author noxx
 */
public final class DiregrafEscort extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have protection from Zombies";

    private static final FilterPermanent filter = new FilterCreaturePermanent("Zombies");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public DiregrafEscort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Diregraf Escort is paired with another creature, both creatures have protection from Zombies.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(new ProtectionAbility(filter), ruleText)));
    }

    private DiregrafEscort(final DiregrafEscort card) {
        super(card);
    }

    @Override
    public DiregrafEscort copy() {
        return new DiregrafEscort(this);
    }
}

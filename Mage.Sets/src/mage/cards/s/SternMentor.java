
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 * @author noxx
 */
public final class SternMentor extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, " +
            "each of those creatures has \"{T}: Target player mills two cards.\"";

    public SternMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Stern Mentor is paired with another creature, each of those creatures has "{T}: Target player puts the top two cards of their library into their graveyard."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(ability, ruleText)));
    }

    private SternMentor(final SternMentor card) {
        super(card);
    }

    @Override
    public SternMentor copy() {
        return new SternMentor(this);
    }
}

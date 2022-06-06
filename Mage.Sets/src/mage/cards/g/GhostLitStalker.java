
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class GhostLitStalker extends CardImpl {

    public GhostLitStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {4}{B}, {tap}: Target player discards two cards. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(2),new ManaCostsImpl<>("{4}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Channel - {5}{B}{B}, Discard Ghost-Lit Stalker: Target player discards four cards. Activate this ability only any time you could cast a sorcery.
        Ability ability2 = new ChannelAbility("{5}{B}{B}", new DiscardTargetEffect(4), TimingRule.SORCERY);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);
    }

    private GhostLitStalker(final GhostLitStalker card) {
        super(card);
    }

    @Override
    public GhostLitStalker copy() {
        return new GhostLitStalker(this);
    }
}

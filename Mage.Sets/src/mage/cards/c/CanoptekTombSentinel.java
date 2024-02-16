package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldFromGraveyardTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CanoptekTombSentinel extends CardImpl {

    public CanoptekTombSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Exile Cannon -- When Canoptek Tomb Sentinel enters the battlefield from a graveyard, exile up to one target nonland permanent.
        Ability ability = new EntersBattlefieldFromGraveyardTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        this.addAbility(ability.withFlavorWord("Exile Cannon"));

        // Unearth {7}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{7}")));
    }

    private CanoptekTombSentinel(final CanoptekTombSentinel card) {
        super(card);
    }

    @Override
    public CanoptekTombSentinel copy() {
        return new CanoptekTombSentinel(this);
    }
}

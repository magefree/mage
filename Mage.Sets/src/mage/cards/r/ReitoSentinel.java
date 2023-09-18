package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReitoSentinel extends CardImpl {

    public ReitoSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Reito Sentinel enters the battlefield, target player mills three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(3));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {3}: Put target card from a graveyard on the bottom of its owner's library.
        ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(
                false, "put target card from a graveyard on the bottom of its owner's library"
        ), new GenericManaCost(3));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private ReitoSentinel(final ReitoSentinel card) {
        super(card);
    }

    @Override
    public ReitoSentinel copy() {
        return new ReitoSentinel(this);
    }
}

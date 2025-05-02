package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.AdventurePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SentinelOfLostLore extends CardImpl {

    private static final FilterOwnedCard filterOwnedCard = new FilterOwnedCard("card you own in exile that has an Adventure");
    private static final FilterCard filterNotOwnedCard = new FilterCard("card you don't own in exile that has an Adventure");

    static {
        filterOwnedCard.add(AdventurePredicate.instance);
        filterNotOwnedCard.add(TargetController.NOT_YOU.getOwnerPredicate());
        filterNotOwnedCard.add(AdventurePredicate.instance);
    }

    public SentinelOfLostLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Sentinel of Lost Lore enters the battlefield, choose one or more —
        // • Return target card you own in exile that has an Adventure to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return target card you own in exile that has an Adventure to your hand"));
        ability.addTarget(new TargetCardInExile(filterOwnedCard));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);

        // • Put target card you don't own in exile that has an Adventure on the bottom of its owner's library.
        Mode mode = new Mode(new PutOnLibraryTargetEffect(false));
        mode.addTarget(new TargetCardInExile(filterNotOwnedCard));
        ability.addMode(mode);

        // • Exile target player's graveyard.
        mode = new Mode(new ExileGraveyardAllTargetPlayerEffect());
        mode.addTarget(new TargetPlayer());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private SentinelOfLostLore(final SentinelOfLostLore card) {
        super(card);
    }

    @Override
    public SentinelOfLostLore copy() {
        return new SentinelOfLostLore(this);
    }
}
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LoamingShaman extends CardImpl {

    public LoamingShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Loaming Shaman enters the battlefield, target player shuffles any number of target cards from their graveyard into their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TargetPlayerShufflesTargetCardsEffect(), false);
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetCardInTargetPlayersGraveyard(Integer.MAX_VALUE));
        this.addAbility(ability);
    }

    private LoamingShaman(final LoamingShaman card) {
        super(card);
    }

    @Override
    public LoamingShaman copy() {
        return new LoamingShaman(this);
    }
}

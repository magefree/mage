package mage.cards.m;

import java.util.UUID;
import mage.MageInt;

import mage.abilities.Ability;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import mage.game.Game;
import mage.players.Player;
import mage.constants.Zone;

/**
 * @author rullinoiz
 */
public final class MirkwoodElk extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elf card");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public MirkwoodElk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Mirkwood Elk enters the battlefield or attacks, return target Elf card from your graveyard to your hand.
        // You gain life equal to that card's power.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility( new MirkwoodElkEffect(), false );
        ability.addTarget( new TargetCardInYourGraveyard(filter) );

        this.addAbility(ability);
    }

    private MirkwoodElk(final MirkwoodElk card) {
        super(card);
    }

    @Override
    public MirkwoodElk copy() {
        return new MirkwoodElk(this);
    }
}

class MirkwoodElkEffect extends OneShotEffect {

    MirkwoodElkEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target Elf card from your graveyard to your hand. You gain life equal to that card's power.";
    }

    public MirkwoodElkEffect(final MirkwoodElkEffect effect) { super(effect); }

    @Override
    public MirkwoodElkEffect copy() { return new MirkwoodElkEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card targetElf = game.getCard(targetPointer.getFirst(game, source));
            if (targetElf != null) {
                controller.moveCards(targetElf, Zone.HAND, source, game);
                controller.gainLife(targetElf.getPower().getValue(), game, source);
            }
            return true;
        }
        return false;
    }
}

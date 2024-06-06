package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class CrypticCoat extends CardImpl {

    public CrypticCoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Cryptic Coat enters the battlefield, cloak the top card of your library, then attach Cryptic Coat to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CrypticCoatEffect()));

        // Equipped creature gets +1/+0 and can't be blocked.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)
                .setText("and can't be blocked")
        );
        this.addAbility(ability);

        // {1}{U}: Return Cryptic Coat to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(), new ManaCostsImpl<>("{1}{U}")));
    }

    private CrypticCoat(final CrypticCoat card) {
        super(card);
    }

    @Override
    public CrypticCoat copy() {
        return new CrypticCoat(this);
    }
}

class CrypticCoatEffect extends OneShotEffect {

    CrypticCoatEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "cloak the top card of your library, then attach {this} to it";
    }

    private CrypticCoatEffect(final CrypticCoatEffect effect) {
        super(effect);
    }

    @Override
    public CrypticCoatEffect copy() {
        return new CrypticCoatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> cloakedList = ManifestEffect.doManifestCards(
                game, source, controller,
                controller.getLibrary().getTopCards(game, 1), true
        );
        if (cloakedList.isEmpty()) {
            return false;
        }
        Permanent cloaked = cloakedList.get(0);
        new AttachEffect(Outcome.BoostCreature, "attach {this} to it")
                .setTargetPointer(new FixedTarget(cloaked, game))
                .apply(game, source);
        return true;
    }
}
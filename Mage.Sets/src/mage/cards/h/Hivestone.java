package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexsandr0x.
 */
public class Hivestone extends CardImpl {

    public Hivestone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Creatures you control are Slivers in addition to their other creature types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CreaturesAreSliversEffect()));
    }

    public Hivestone(final Hivestone card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new Hivestone(this);
    }

}

class CreaturesAreSliversEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();


    public CreaturesAreSliversEffect() {

        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "All permanents are artifacts in addition to their other types";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent perm : permanents) {
            List<String> cardSubType = perm.getSubtype(game);
            if (!cardSubType.contains("Sliver")) {
                cardSubType.add("Sliver");
            }
        }
        return true;
    }

    @Override
    public mage.cards.h.CreaturesAreSliversEffect copy() {
        return new mage.cards.h.CreaturesAreSliversEffect(this);
    }

    private CreaturesAreSliversEffect(mage.cards.h.CreaturesAreSliversEffect effect) {
        super(effect);
    }
}


package mage.cards.p;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public final class ProfaneProcession extends CardImpl {

    public ProfaneProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.t.TombOfTheDuskRose.class;

        // {3}{W}{B}: Exile target creature. Then if there are three or more cards exiled with Profane Procession, transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProfaneProcessionEffect(), new ManaCostsImpl<>("{3}{W}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ProfaneProcession(final ProfaneProcession card) {
        super(card);
    }

    @Override
    public ProfaneProcession copy() {
        return new ProfaneProcession(this);
    }
}

class ProfaneProcessionEffect extends OneShotEffect {

    public ProfaneProcessionEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature. Then if there are three or more cards exiled with {this}, transform it.";
    }

    public ProfaneProcessionEffect(final ProfaneProcessionEffect effect) {
        super(effect);
    }

    @Override
    public ProfaneProcessionEffect copy() {
        return new ProfaneProcessionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && exileId != null && sourceObject != null) {
            new ExileTargetEffect(exileId, sourceObject.getIdName()).setTargetPointer(targetPointer).apply(game, source);
            game.getState().processAction(game);
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null && exileZone.size() > 2) {
                new TransformSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}
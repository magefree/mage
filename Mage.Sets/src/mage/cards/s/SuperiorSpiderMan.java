package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SuperiorSpiderMan extends CardImpl {

    public SuperiorSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mind Swap -- You may have Superior Spider-Man enter as a copy of any creature card in a graveyard, except his name is Superior Spider-Man and he's a 4/4 Spider Human Hero in addition to his other types. When you do, exile that card.
        this.addAbility(new EntersBattlefieldAbility(new SuperiorSpiderManCopyEffect(), true));
    }

    private SuperiorSpiderMan(final SuperiorSpiderMan card) {
        super(card);
    }

    @Override
    public SuperiorSpiderMan copy() {
        return new SuperiorSpiderMan(this);
    }
}

class SuperiorSpiderManCopyEffect extends OneShotEffect {

    SuperiorSpiderManCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "as a copy of any creature card in a graveyard, except his name is Superior Spider-Man " +
                "and he's a 4/4 Spider Human Hero in addition to his other types. When you do, exile that card.";
    }

    private SuperiorSpiderManCopyEffect(final SuperiorSpiderManCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard"));
            target.withNotTarget(true);
            if (target.canChoose(source.getControllerId(), source, game)) {
                player.choose(outcome, target, source, game);
                Card copyFromCard = game.getCard(target.getFirstTarget());
                if (copyFromCard != null) {
                    Permanent newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                    newBluePrint.assignNewId();
                    SuperiorSpiderManCopyApplier applier = new SuperiorSpiderManCopyApplier();
                    applier.apply(game, newBluePrint, source, source.getSourceId());
                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, source.getSourceId());
                    game.addEffect(copyEffect, source);

                    ReflexiveTriggeredAbility triggeredAbility = new ReflexiveTriggeredAbility(
                            new ExileTargetEffect().setTargetPointer(new FixedTarget(copyFromCard.getId())),
                            false
                    );
                    game.fireReflexiveTriggeredAbility(triggeredAbility, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SuperiorSpiderManCopyEffect copy() {
        return new SuperiorSpiderManCopyEffect(this);
    }
}

class SuperiorSpiderManCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.setName("Superior Spider-Man");
        blueprint.getPower().setModifiedBaseValue(4);
        blueprint.getToughness().setModifiedBaseValue(4);
        blueprint.addSubType(SubType.SPIDER, SubType.HUMAN, SubType.HERO);
        return true;
    }
}

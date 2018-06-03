
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class SkillBorrower extends CardImpl {

    public SkillBorrower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card.
        this.addAbility(new SkillBorrowerAbility());
    }

    public SkillBorrower(final SkillBorrower card) {
        super(card);
    }

    @Override
    public SkillBorrower copy() {
        return new SkillBorrower(this);
    }
}


class SkillBorrowerAbility extends StaticAbility {

    public SkillBorrowerAbility() {
        super(Zone.BATTLEFIELD, new SkillBorrowerEffect());
    }

    public SkillBorrowerAbility(SkillBorrowerAbility ability) {
        super(ability);
    }

    @Override
    public SkillBorrowerAbility copy() {
        return new SkillBorrowerAbility(this);
    }

    @Override
    public String getRule() {
        return "As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card";
    }
}

class SkillBorrowerEffect extends ContinuousEffectImpl {

    public SkillBorrowerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card";
    }

    public SkillBorrowerEffect(final SkillBorrowerEffect effect) {
        super(effect);
    }


    @Override
    public SkillBorrowerEffect copy() {
        return new SkillBorrowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            Card card = player.getLibrary().getFromTop(game);
            if(card != null && (card.isCreature() || card.isArtifact())){
                Permanent permanent = game.getPermanent(source.getSourceId());
                if(permanent != null){
                    for(Ability ability : card.getAbilities()){
                        if(ability instanceof ActivatedAbility){
                            permanent.addAbility(ability, source.getSourceId(), game);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}

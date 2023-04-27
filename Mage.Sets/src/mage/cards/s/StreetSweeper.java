
package mage.cards.s;
 
import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
 
/**
 *
 * @author LevelX2
 */
public final class StreetSweeper extends CardImpl {
 
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures with defender you control");
 
    static{
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }
 
    public StreetSweeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.CONSTRUCT);
 
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);
 

        // Whenever Street Sweeper attacks, destroy all Auras attached to target land.
        Ability ability = new AttacksTriggeredAbility(new StreetSweeperDestroyEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
 
    }
 
    private StreetSweeper(final StreetSweeper card) {
        super(card);
    }
 
    @Override
    public StreetSweeper copy() {
        return new StreetSweeper(this);
    }
}
 
class StreetSweeperDestroyEffect extends OneShotEffect {
 
    public StreetSweeperDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all Auras attached to target land";
    }
 
    public StreetSweeperDestroyEffect(final StreetSweeperDestroyEffect effect) {
        super(effect);
    }
 
    @Override
    public StreetSweeperDestroyEffect copy() {
        return new StreetSweeperDestroyEffect(this);
    }
 
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if(permanent != null)
        {
            LinkedList<UUID> attachments = new LinkedList<>();
            attachments.addAll(permanent.getAttachments());
            for(UUID uuid : attachments)
            {
                Permanent aura = game.getPermanent(uuid);
                if(aura != null && aura.hasSubtype(SubType.AURA, game))
                {
                    aura.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
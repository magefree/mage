
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SigardasAid extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("an Equipment");
    private static final FilterCard filterCard = new FilterCard("Aura and Equipment spells");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filterCard.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
    }

    public SigardasAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // You may cast Aura and Equipment spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filterCard, false)));

        // Whenever an Equipment enters the battlefield under your control, you may attach it to target creature you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new SigardasAidEffect(), filter, true, SetTargetPointer.PERMANENT);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

    }

    private SigardasAid(final SigardasAid card) {
        super(card);
    }

    @Override
    public SigardasAid copy() {
        return new SigardasAid(this);
    }
}

class SigardasAidEffect extends OneShotEffect {

    public SigardasAidEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may attach it to target creature you control";
    }

    private SigardasAidEffect(final SigardasAidEffect effect) {
        super(effect);
    }

    @Override
    public SigardasAidEffect copy() {
        return new SigardasAidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent targetCreature = game.getPermanent(source.getTargets().getFirstTarget());
            if (equipment != null && targetCreature != null) {
                targetCreature.addAttachment(equipment.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}

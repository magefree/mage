
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class KorOutfitter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Equipment you control");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public KorOutfitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kor Outfitter enters the battlefield, you may attach target Equipment you control to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EquipEffect(), true);
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private KorOutfitter(final KorOutfitter card) {
        super(card);
    }

    @Override
    public KorOutfitter copy() {
        return new KorOutfitter(this);
    }
}

class EquipEffect extends OneShotEffect {

    public EquipEffect() {
        super(Outcome.BoostCreature);
        staticText = "attach target Equipment you control to target creature you control";
    }

    private EquipEffect(final EquipEffect effect) {
        super(effect);
    }

    @Override
    public EquipEffect copy() {
        return new EquipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null && equipment != null) {
            return creature.addAttachment(equipment.getId(), source, game);
        }
        return false;
    }
}

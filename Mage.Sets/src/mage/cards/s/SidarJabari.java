
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SidarJabari extends CardImpl {

    private final UUID originalId;

    public SidarJabari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // Whenever Sidar Jabari attacks, tap target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public SidarJabari(final SidarJabari card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(ability.getSourceId());
            filter.add(new ControllerIdPredicate(defenderId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            ability.addTarget(target);
        }
    }

    @Override
    public SidarJabari copy() {
        return new SidarJabari(this);
    }
}

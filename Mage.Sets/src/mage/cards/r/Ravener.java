package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.MustAttackOpponentWithCreatureTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ravener extends CardImpl {

    public Ravener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Ravenous
        this.addAbility(new RavenousAbility());

        // When Ravener enters the battlefield, target creature attacks target opponent this turn if able.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MustAttackOpponentWithCreatureTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Ravener(final Ravener card) {
        super(card);
    }

    @Override
    public Ravener copy() {
        return new Ravener(this);
    }
}

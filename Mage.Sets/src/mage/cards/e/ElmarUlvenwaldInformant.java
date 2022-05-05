package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElmarUlvenwaldInformant extends CardImpl {

    public ElmarUlvenwaldInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast your second spell each turn, untap target creature, then investigate.
        Ability ability = new CastSecondSpellTriggeredAbility(new UntapTargetEffect());
        ability.addEffect(new InvestigateEffect().concatBy(", then"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private ElmarUlvenwaldInformant(final ElmarUlvenwaldInformant card) {
        super(card);
    }

    @Override
    public ElmarUlvenwaldInformant copy() {
        return new ElmarUlvenwaldInformant(this);
    }
}

package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttentiveSkywarden extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.INCUBATOR, "Incubator token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public AttentiveSkywarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Attentive Skywarden deals combat damage to a player or battle, transform up to one target Incubator token you control.
        Ability ability = new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(new TransformTargetEffect(), false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private AttentiveSkywarden(final AttentiveSkywarden card) {
        super(card);
    }

    @Override
    public AttentiveSkywarden copy() {
        return new AttentiveSkywarden(this);
    }
}

package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BragoKingEternal extends CardImpl {

    public BragoKingEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT, SubType.NOBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Brago, King Eternal deals combat damage to a player, exile any number of target nonland permanents you control, then return those cards to the battlefield under their owner's control.
        Effect effect = new ExileTargetEffect(this.getId(), this.getName(), Zone.BATTLEFIELD);
        effect.setText("exile any number of target nonland permanents you control");
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false);
        FilterControlledPermanent filterControlledNonlandPermanent = new FilterControlledPermanent();
        filterControlledNonlandPermanent.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        ability.addTarget(new TargetControlledPermanent(0, Integer.MAX_VALUE, filterControlledNonlandPermanent, false));
        ability.addEffect(new ReturnFromExileEffect(this.getId(), Zone.BATTLEFIELD, ", then return those cards to the battlefield under their owner's control"));
        this.addAbility(ability);
    }

    public BragoKingEternal(final BragoKingEternal card) {
        super(card);
    }

    @Override
    public BragoKingEternal copy() {
        return new BragoKingEternal(this);
    }
}

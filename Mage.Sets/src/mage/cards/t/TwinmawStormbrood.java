package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.OmenCard;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class TwinmawStormbrood extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TwinmawStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{W}", "Charring Bite", "{1}{R}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, you gain 5 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5)));

        // Charring Bite
        // deals 5 damage to target creature without flying.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(filter));
        this.finalizeOmen();
    }

    private TwinmawStormbrood(final TwinmawStormbrood card) {
        super(card);
    }

    @Override
    public TwinmawStormbrood copy() {
        return new TwinmawStormbrood(this);
    }
}

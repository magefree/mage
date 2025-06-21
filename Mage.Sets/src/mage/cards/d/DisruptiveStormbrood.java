package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.OmenCard;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class DisruptiveStormbrood extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 3));
    }

    public DisruptiveStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{G}", "Petty Revenge", "{1}{B}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, destroy up to one target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // Petty Revenge
        // Destroy target creature with power 3 or less.
        Effect spellEffect = new DestroyTargetEffect();
        this.getSpellCard().getSpellAbility().addEffect(spellEffect);
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(filter));
        this.finalizeOmen();
    }

    private DisruptiveStormbrood(final DisruptiveStormbrood card) {
        super(card);
    }

    @Override
    public DisruptiveStormbrood copy() {
        return new DisruptiveStormbrood(this);
    }
}

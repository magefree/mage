package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdagiaWindsweptBastion extends CardImpl {

    public AdagiaWindsweptBastion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLANET);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 12+
        // {3}{W}, {T}: Create a token that's a copy of target artifact or enchantment you control, except it's legendary. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenCopyTargetEffect()
                        .setPermanentModifier(token -> token.addSuperType(SuperType.LEGENDARY))
                        .setText("create a token that's a copy of target artifact or enchantment you control, except it's legendary"),
                new ManaCostsImpl<>("{3}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(new StationLevelAbility(12).withLevelAbility(ability));
    }

    private AdagiaWindsweptBastion(final AdagiaWindsweptBastion card) {
        super(card);
    }

    @Override
    public AdagiaWindsweptBastion copy() {
        return new AdagiaWindsweptBastion(this);
    }
}
